import cart from '/js/cart.js';

const getProduct = function (page, size, callback) {
    let xhr = new XMLHttpRequest();
    xhr.open("GET", `/api/v1/products?page=${page}&size=${size}`);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                callback(JSON.parse(this.response));
            } else {
                console.log(this.response)
            }
        }
    }

    xhr.send();
}

const createProductEle = function (product) {
    let ele = document.createElement("div");
    let template = `
        <a href="#">
            <div class="product">
                <div class="preview">
                    <div class="img-container">
                        <img src="${product.preview}">
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
    const rootEle = document.getElementById("product-list");

    for (let product of products) {
        rootEle.appendChild(createProductEle(product));
    }
}

let curProductPage = 0;
const PRODUCT_PAGE_SIZE = 12;
const loadProduct = function () {
    getProduct(curProductPage, PRODUCT_PAGE_SIZE, renderProduct);
    curProductPage++;
}

window.onload = function() {
    loadProduct();

    const loadProductBtn = document.getElementById("load-product-btn");
    loadProductBtn.addEventListener("click", loadProduct);
}