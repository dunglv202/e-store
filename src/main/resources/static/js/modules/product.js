import ajaxRequest from "./ajaxrequest.js";

const productService = {
    getAll : function (page, size, callback) {
        if (page < 0) return null;
        ajaxRequest.get("/api/v1/products",`page=${page}&size=${size}`,callback);
    },

    delete : function (productId, callback) {
        ajaxRequest.delete(`/api/v1/products/${productId}`, null, callback);
    },

    getImages : function (productId, page, size, callback) {
        ajaxRequest.get(`/api/v1/products/${productId}/images`, `page=${page}&size=${size}`, callback);
    },

    deleteImage : function (productId, imageId, callback) {
        ajaxRequest.delete(`/api/v1/products/${productId}/images/${imageId}`, null, callback)
    }
}

export default productService;