import ajaxRequest from "/js/modules/ajaxrequest.js";

const review = {
    getAll: function(productId, page, size, callback) {
        if (page < 0) return null;
        ajaxRequest.get(`/api/v1/products/${productId}/reviews`, `page=${page}&size=${size}`, callback);
    }
};

export default review;