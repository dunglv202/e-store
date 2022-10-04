import ajaxRequest from "/js/modules/ajaxrequest.js";

const ORDER_PER_LOAD = 10;

const getOrders = function (page, size, params, callback) {
    ajaxRequest.get(`/api/v1/orders`, params, callback);
}

const updateStatus = function (orderId, status, callback) {
    ajaxRequest.doPut(`/api/v1/orders/${orderId}`, status, callback);
}

const createOrderEle = function (order) {
    let ctn = document.createElement("li");
    ctn.classList.add("order");

    let products = "";
    let isCompleted = true;
    products += `#${order.id}: `;
    for (let item of order.items){
        products += item.product.name + ", ";
        if (products.length >= 63) {
            isCompleted = false;
            break;
        }
    }
    products = products.slice(0, 60);
    if (!isCompleted) products += "...";

    let statusSelection = "";
    for (let status of orderStatusList) {
        statusSelection += `<option value="${status}">${status}</option>`;
    }

    ctn.innerHTML = `
        <a href="/manage/orders/${order.id}"><h2 class="name">
            ${products}
        </h2></a>
        <div class="date">
            ${order.dateCreated}
        </div>
        <div class="price currency">
            ${new Intl.NumberFormat('en-US').format(order.total)}
        </div>
        <div class="status pending">
            <select>${statusSelection}</select>
        </div>
    `;

    let statusSelector = ctn.querySelector(".status select");
    statusSelector.value = order.status;
    statusSelector.onchange = function() {
        let oldValue = order.status;
        updateStatus(order.id, this.value, function (success, updated) {
            if (success) {
                order.status = updated.status;
                statusSelector.classList.add("updated");
            } else {
                statusSelector.value = oldValue;
            }
        });
    };

    return ctn;
}

const renderOrders = function (orders) {
    let ordersContainer = document.getElementById("order-list");

    for (let order of orders) {
        ordersContainer.appendChild(createOrderEle(order));
    }
}

let curOrderPage = 0;
const loadOrders = function () {
    const status = document.getElementById("status-filter");
    let params = status.value;
    if (params !== "ALL") params = `status=${params}`;
    params += `&page=${curOrderPage}&size=${ORDER_PER_LOAD}`;

    getOrders(curOrderPage, ORDER_PER_LOAD, params, (orders) => {
        curOrderPage++;
        renderOrders(orders);
    });
}

const findOrder = function () {
    const orderId = document.getElementById("search").value;
    document.getElementById("order-list").innerHTML = "";
    ajaxRequest.get(`/api/v1/orders/${orderId}`, "", (order) => {renderOrders([order])});
}

window.onload = function () {
    loadOrders();

    const statusFilter = document.getElementById("status-filter");
    statusFilter.addEventListener("change", () => {
        document.getElementById("order-list").innerHTML = "";
        curOrderPage = 0;
        loadOrders();
    });

    const searchForm = document.getElementById("search-form");
    searchForm.addEventListener("submit", function (e) {
        e.preventDefault();
        findOrder();
    });

    const moreOrderBtn = document.getElementById("more-order-btn");
    moreOrderBtn.addEventListener("click", loadOrders);
}