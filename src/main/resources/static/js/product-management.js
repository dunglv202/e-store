import productService from "/js/modules/product.js";

const PRODUCT_PAGE_SIZE = 7;

let curProductPage = 0;

const currencyFormat = function (num) {
    return new Intl.NumberFormat('en-US').format(num);
}

const createProductEle = function (product) {
    let ele = document.createElement("tr");
    let template = `
        <td>
            <div class="img-container">
                <img src="/${product.preview}">
            </div>
        </td>
        <td class="name"><a href="/products/${product.id}">${product.name}</a></td>
        <td>${product.description.slice(0,40) + (product.description.length>40 ? '...' : '')}</td>
        <td class="currency">${currencyFormat(product.price)}</td>
        <td>${product.quantity}</td>
        <td>${product.category.name}</td>
        <td>${product.brand.name}</td>
        
        <td class="actions">
            <div class="center">
                <a href="/manage/products/edit/${product.id}">
                    <button class="btn edit">Edit</button>
                </a>
                <button class="btn remove">Remove</button>
            </div>
        </td>
    `;
    ele.innerHTML = template;

    let removeBtn = ele.querySelector(".remove");
    removeBtn.addEventListener("click", function () {
        if (confirm("Delete " + product.name + " from inventory? This action can't be undo!")) {
            productService.delete(product.id, () => {
                ele.remove();
                alert("Deleted");
                loadProducts(0, renderProducts);
            });
        }
    })

    return ele;
}

const renderProducts = function (products) {
    if (!products || products.length == 0) return;
    const rootEle = document.querySelector("#item-table tbody");
    rootEle.innerHTML = "";
    for (let product of products) {
        rootEle.appendChild(createProductEle(product));
    }
}

const loadProducts = function (direction, callback) {
    productService.getAll(curProductPage+direction, PRODUCT_PAGE_SIZE, function(obj) {
        if (obj && obj.length !== 0)
            curProductPage += direction;
        callback(obj);
    });
}

window.onload = function () {
    loadProducts(0, renderProducts);

    let prevPageBtn = document.querySelector(".prev");
    prevPageBtn.addEventListener("click", () => loadProducts(-1, renderProducts));
    let nextPageBtn = document.querySelector(".next");
    nextPageBtn.addEventListener("click", () => loadProducts(1, renderProducts));
}