class Request:
    text = ""
    args = []
    location = ""

class Response:
    code = 0
    short = ""
    long = ""
    page = ""
    image = ""

class MError(Exception):
    code = 9
    reason = ""

    def __init__(self, reason, code = 9, message = "Something Failed"):
        super(Exception, self).__init__(message)
        self.code = code
        self.reason = reason
