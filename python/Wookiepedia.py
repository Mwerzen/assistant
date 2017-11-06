import requests
import json

from Containers import Request, Response, MError
from Utils import debug

def build_search_url(searchTerms):
    terms = build_search_terms(searchTerms)
    url = "http://starwars.wikia.com/api/v1/Search/List/?query=" + terms + "&limit=25&namespaces=0%2C14"
    debug("Search URL: " + url)
    return url

def build_search_terms(searchTerms):
    terms = ""
    for term in searchTerms:
        terms = terms + term + " "
    terms = terms.lstrip().rstrip().replace(' ', '+')
    debug("Search Terms: " + terms)
    return terms

def build_detail_url(id):
    url =  "http://starwars.wikia.com/api/v1/Articles/Details/?ids=" + str(id) + "&abstract=500&width=200&height=200"
    debug("Detail URL: " + url)
    return url

def check_response(response):
    if not response.ok:
        raise MError("Invalid Response from Wookiepedia")

def get_wookiepedia_abstract(request):
    response = requests.get(build_search_url(request.args))
    check_response(response)
    body = json.loads(response.content)

    article_id = body["items"][0]["id"]

    response = requests.get(build_detail_url(article_id))
    check_response(response)
    body = json.loads(response.content)

    resp = Response()
    resp.short = body["items"][str(article_id)]["title"]
    resp.long = body["items"][str(article_id)]["abstract"]
    resp.page = body["basepath"] + body["items"][str(article_id)]["url"]
    resp.image = body["items"][str(article_id)]["thumbnail"]
    return resp