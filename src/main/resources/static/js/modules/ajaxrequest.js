const makeRequest = function (method, endpoint, callback) {
    let xhr = new XMLHttpRequest();
    xhr.open(method, endpoint);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            switch (this.status) {
                case 200:
                    callback(JSON.parse(this.response));
                    break;
                case 401:
                    location.href = "/login";
                    break;
                default:
                    alert("Something went wrong, check console for more details");
                    console.log(this.response);
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

const sendPut = function (endpoint, body, callback) {
    let xhr = makeRequest("PUT", endpoint, callback);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(body));
}

const sendDelete = function (endpoint, paramSequence, callback) {
    let xhr = makeRequest("DELETE", `${endpoint}?${paramSequence}`, callback);
    xhr.send();
}

const ajaxRequest = {
    get : sendGet,
    post : sendPost,
    put : sendPut,
    delete : sendDelete
}

export default ajaxRequest;