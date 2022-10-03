import cart from '/js/cart.js';
import ajaxRequest from "/js/modules/ajaxrequest.js";

let curProductPage = 0;
const PRODUCT_PAGE_SIZE = 12;
let paramSequence = "";

const createProductEle = function (product) {
    let ele = document.createElement("div");
    let template = `
        <a href="/products/${product.id}">
            <div class="product">
                <div class="preview">
                    <div class="img-container">
                        <img src="/${product.preview}">
                    </div>
                    <div class="actions">
                        <button>Quick View</button>
                        <button class="add-to-cart-btn">Add To Cart</button>
                    </div>
                </div>
                <div class="basic-info">
                    <h2 class="name">${product.name}</h2>
                    <div class="price currency">${product.price}</div>
                </div>
            </div>
        </a>
    `;
    ele.classList.add("grid-item");
    ele.innerHTML = template;

    ele.querySelector(".add-to-cart-btn").addEventListener("click", function(e) {
        e.preventDefault();
        cart.addToCart({
            "product": {
                "id": product.id
            },
            "quantity": 1
        }, function(addedItem) {
            alert("Added " + addedItem.product.name + " to cart.");
        })
    });

    return ele;
}

const renderProduct = function (products) {
    if (products === null || products.length === 0) return;
    const rootEle = document.getElementById("product-list");

    for (let product of products) {
        rootEle.appendChild(createProductEle(product));
    }
}

const initCriteria = function () {
    const filterForm = document.getElementById("filters");

    // make request params
    let formData = new FormData(filterForm);
    paramSequence = "";
    if (formData.get("keyword"))
        paramSequence += "search&";
    for (let param of formData.entries()) {
        if (param[1])
            paramSequence += `${param[0]}=${param[1]}&`;
    }

    paramSequence = paramSequence.slice(0, -1);
}

const loadProduct = function () {
    // send get and render
    ajaxRequest.get("/api/v1/products", `${paramSequence}&page=${curProductPage}&size=${PRODUCT_PAGE_SIZE}`, function (products) {
        curProductPage++;
        renderProduct(products);
    });
}

window.onload = function() {
    initCriteria();

    loadProduct();

    // load product when click btn
    const loadProductBtn = document.getElementById("load-product-btn");
    loadProductBtn.addEventListener("click", loadProduct);

    // open filter form
    const filterForm = document.getElementById("filters");
    document.getElementById("open-filter").addEventListener("click", function () {
        filterForm.classList.remove("hidden");
    })

    // close dialog...
    let closeBtns = document.getElementsByClassName("close-btn");
    for (let btn of closeBtns)
        btn.addEventListener("click", function() {this.parentElement.classList.add("hidden")});

    // do filter
    filterForm.addEventListener("submit", function(e) {
        e.preventDefault();
        initCriteria();
        let queryIndex = location.href.lastIndexOf("?");
        if (queryIndex === -1) queryIndex = location.href.length;
        location.href = location.href.slice(0, queryIndex) + "?" + paramSequence;
    });
}