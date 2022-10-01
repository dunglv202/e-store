import cart from "/js/cart.js";
import order from "/js/order.js";

let cartItems;

const formatCurrency = function (num) {
    return new Intl.NumberFormat('en-US').format(num);
}

const createItemEle = function (item) {
    let ele = document.createElement("tr");
    let template = `
        <td>${item.product.name} x ${item.quantity}</td>
        <td class="currency">${formatCurrency(item.total)}</td>
    `;
    ele.classList.add("order-item");
    ele.innerHTML = template;

    return ele;
}

const renderItems = function (items) {
    const root = document.querySelector("#item-list tbody");
    let total = 0;
    for (let item of items) {
        total += item.total;
        root.appendChild(createItemEle(item));
    }

    let orderTotalEle = document.querySelector("#total .value");
    orderTotalEle.innerText = formatCurrency(total);
}

window.onload = function () {
    cart.getAllItems(0, 100, (items) => {
        cartItems = items;
        console.log(items);
        renderItems(items);
    });

    const orderForm = document.getElementById("order-form");
    orderForm.addEventListener("submit", function (e) {
        e.preventDefault();

        // make order
        let data = Object.fromEntries(new FormData(this).entries());

        let orderObj = {
            "items": cartItems,
            "recipient": {
                "name": data["recipient"],
                "phoneNumber": data["recipientPhoneNumber"],
                "address": data["deliveryLocation"]
            },
            "noted": data["additionalNotes"],
            "payment": data["methodOfPayment"]
        }

        order.makeOrder(orderObj, () => {
            alert("Success!");
            location.href = "/cart";
        });
    })
}