const makeRequest = function (method, endpoint, callback, callBackIfFail) {
    let xhr = new XMLHttpRequest();
    xhr.open(method, endpoint);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            switch (this.status) {
                case 200:
                    if (callBackIfFail) {
                        callback(true, JSON.parse(this.response));
                    } else {
                        callback(JSON.parse(this.response));
                    }
                    break;
                case 401:
                    location.href = "/login";
                    break;
                case 404:
                    if (callBackIfFail) callback(false, JSON.parse(this.response));
                    break;
                default:
                    alert(JSON.parse(this.response).messages);
                    console.log(this.response);
                    if (callBackIfFail) {
                        callback(false, JSON.parse(this.response));
                    }
            }
        }
    }

    return xhr;
}

const sendPost = function (endpoint, body, callback) {
    let xhr = makeRequest("POST", endpoint, callback);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(body));
}

const sendGet = function (endpoint, paramSequence, callback) {
    let xhr = makeRequest("GET", `${endpoint}?${paramSequence}`, callback);
    xhr.send();
}

const sendPut = function (endpoint, body, callback, callBackIfFail) {
    let xhr = makeRequest("PUT", endpoint, callback, callBackIfFail);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(body));
}

const sendDelete = function (endpoint, paramSequence, callback) {
    let xhr = makeRequest("DELETE", `${endpoint}?${paramSequence}`, callback);
    xhr.send();
}

const doPut = function () {

}

const ajaxRequest = {
    get : sendGet,
    post : sendPost,
    put : (endpoint, body, callback) => sendPut(endpoint, body, callback, false),
    doPut : (endpoint, body, callback) => sendPut(endpoint, body, callback, true),
    delete : sendDelete
}

export default ajaxRequest;