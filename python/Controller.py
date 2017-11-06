import requests
import json
import sys
import hashlib
import time
from flask import Flask, jsonify, request

from Containers import Request, Response, MError
from Wookiepedia import get_wookiepedia_abstract
from Secret import get_secret
from Utils import debug

app = Flask(__name__)

def auth():
    token = request.headers.get('token')

    date,key = token.split("-")
    computed_key = hash(date)

    currTime = int(round(time.time() * 1000))
    timeDiff = abs(int(date) - currTime)

    debug("Date: " + date + " CurrDate: " + str(currTime) + " Diff: " + str(timeDiff))
    debug("Token: " + key + " ComputedToken: " + computed_key)

    if not key == computed_key or timeDiff > 30000:
        raise MError("Unable to authenticate.")



def hash(text):
    m = hashlib.sha1()
    m.update((get_secret() + text).encode('utf-8'))
    return m.hexdigest()

def build_request():
    js = request.get_json()
    req = Request()
    req.text = js.get("text", "")
    req.args = js.get("args", "")
    if not isinstance(req.args, (list, tuple)):
        req.args = [req.args]
    req.location = js.get("location", "")
    return req

def build_error(cause, code = 9):
    error = Response()
    error.code = code
    error.short = cause
    error.long = ""
    error.page = ""
    error.image = ""
    return error

def build_response(res):
    data = {}
    debug(res)
    data["code"] = res.code
    data["short"] = res.short
    data["long"] = res.long
    data["page"] = res.page
    data["image"] = res.image
    return jsonify(data)

@app.errorhandler(403)
@app.errorhandler(404)
@app.errorhandler(410)
@app.errorhandler(500)
def error(e):
    return build_response(build_error("An error occurred"))

@app.route('/wookie', methods=['POST'])
def wookiepedia():
    res = {}
    try:
        auth()
        req = build_request()
        res = get_wookiepedia_abstract(req)
    except MError as exc:
        debug(exc.reason)
        res = build_error(exc.reason, exc.code)
    except:
        e = sys.exc_info()[0]
        debug(e)
        debug(e.args[0])
        res = build_error("Wookiepedia Failed")
    finally:
        return build_response(res)


if __name__ == '__main__':
    app.run()