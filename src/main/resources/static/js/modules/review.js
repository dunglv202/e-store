import ajaxRequest from "/js/modules/ajaxrequest.js";

const review = {
    getAll: function(productId, page, size, callback) {
        if (page < 0) return null;
        ajaxRequest.get(`/api/v1/products/${productId}/reviews`, `page=${page}&size=${size}`, callback);
    },
    add: function(orderItemId, rating, comment, callback) {
        const review = {
            "rating": rating,
            "comment": comment,
            "orderItem": {
                "id": orderItemId
            }
        };
        ajaxRequest.doPost(`/api/v1/reviews`, review, callback);
    },
    update: function (reviewId, rating, comment, callback) {
        const review = {
            "rating": rating,
            "comment": comment
        };
        ajaxRequest.doPut(`/api/v1/reviews/${reviewId}`, review, callback);
    }
};

export default review;