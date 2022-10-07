import ajaxRequest from "/js/modules/ajaxrequest.js";

const ORDER_PER_LOAD = 10;

const getOrders = function (page, size, params, callback) {
    ajaxRequest.get(`/api/v1/orders`, params, callback);
}

const createOrderEle = function (order) {
    let ctn = document.createElement("a");
    ctn.href = `/order/history/${order.id}`;

    let products = `#${order.id}: `;
    let isCompleted = true;
    for (let item of order.items){
        products += item.product.name + ", ";
        if (products.length >= 63) {
            isCompleted = false;
            break;
        }
    }
    products = products.slice(0, 60);
    if (!isCompleted) products += "...";

    ctn.innerHTML = `
        <li class="order">
            <h2 class="name">
                ${products}
            </h2>
            <div class="date">
                ${order.dateCreated}
            </div>
            <div class="price currency">
                ${new Intl.NumberFormat('en-US').format(order.total)}
            </div>
            <div class="status ${order.status.toLowerCase()}">
                ${order.status}
            </div>
        </li>
    `;
    if (order.status === "RECEIVED") {
        let rateBtn = document.createElement("a");
        rateBtn.href = `/order/history/${order.id}/review`;
        rateBtn.classList.add("leave-review")
        rateBtn.innerHTML = "Review"
        ctn.querySelector("li .status").appendChild(rateBtn);
    }
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
    const status = document.querySelector("#status-filter .tab.active");
    let params = status.getAttribute("th-status");
    if (params !== "ALL") params = `status=${params}`;
    params += `&sortedBy=BY_NEWEST&page=${curOrderPage}&size=${ORDER_PER_LOAD}`;

    getOrders(curOrderPage, ORDER_PER_LOAD, params, (orders) => {
        curOrderPage++;
        renderOrders(orders);
    });
}

const enableStatusFilter = function () {
    const statusTabs = document.querySelectorAll("#status-filter .tab");
    let ordersContainer = document.getElementById("order-list");

    for (let tab of statusTabs) {
        tab.addEventListener("click", function () {
            for (let t of statusTabs) t.classList.remove("active");
            tab.classList.add("active");
            curOrderPage = 0;
            ordersContainer.innerHTML = "";
            loadOrders();
        });
    }
}

window.onload = function () {
    enableStatusFilter();
    loadOrders();

    const moreOrderBtn = document.getElementById("more-order-btn");
    moreOrderBtn.addEventListener("click", loadOrders);
}