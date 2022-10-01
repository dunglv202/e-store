import ajaxRequest from "/js/modules/ajaxrequest.js";

const makeOrder = function (order, callback) {
    ajaxRequest.post("/api/v1/orders", order, callback)
}

const order = {
    makeOrder : makeOrder
}

export default order;