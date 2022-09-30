import cart from "/js/cart.js";

let updatedItems = {
    "changed": new Map(),
    "removed": new Set()
};

const currencyFormat = function (num) {
    return new Intl.NumberFormat('en-US').format(num);
}

const createItemEle = function (item) {
    let ele = document.createElement("tr");
    let template = `
        <td>
            <div class="img-container">
                <img src="${item.product.preview}">
            </div>
        </td>
        <td class="name">
            ${item.product.name}
        </td>
        <td class="currency">${currencyFormat(item.product.price)}</td>
        <td>
            <div class="center">
                <div class="number-selector">
                    <div class="decrease-btn btn">-</div>
                    <input class="quantity" type="number" value="${item.quantity}">
                    <div class="increase-btn btn">+</div>
                </div>
            </div>
        </td>
        <td class="currency subtotal" style="font-weight: 600; text-align: right">${currencyFormat(item.total)}</td>
        <td class="actions">
            <div class="center">
                <button class="btn remove">Remove</button>
            </div>
        </td>
    `;
    ele.innerHTML = template;

    // update quantity
    let quantityEle = ele.querySelector(".quantity");
    let decreaseBtn = ele.querySelector(".decrease-btn");
    let increaseBtn = ele.querySelector(".increase-btn");
    let subtotal = ele.querySelector(".subtotal");

    const changeQuantity = function(amount) {
        quantityEle.value -= -amount;
        if (Number(quantityEle.value) < 1)
            quantityEle.value = 1;

        updatedItems.changed.set(item.id, quantityEle.value);

        // refresh total
        subtotal.innerHTML = currencyFormat(Number(quantityEle.value) * item.product.price);
    }

    decreaseBtn.addEventListener("click", () => {
        changeQuantity(-1);
    });
    increaseBtn.addEventListener("click", () => {
        changeQuantity(+1);
    });
    quantityEle.addEventListener("input", () => {
        changeQuantity(0);
    });

    // remove item
    let removeBtn = ele.querySelector(".actions .remove");
    removeBtn.addEventListener("click", function () {
        ele.remove();
        updatedItems.removed.add(item.id);
    });

    return ele;
}

const renderItems = function (items) {
    const rootEle = document.querySelector("#item-table tbody");
    for (let item of items) {
        rootEle.appendChild(createItemEle(item));
    }
}

const updateCart = function () {
    // update quantity
    updatedItems.changed.forEach((val, key) => {
        cart.updateItem({
            "id": key,
            "quantity": val
        }, function (response) {
            console.log("Updated ", response.product.name);
        });
    });
    updatedItems.changed.clear();

    // delete items
    updatedItems.removed.forEach((val) => {
        cart.deleteItem(val, function (response) {
            console.log("Delete ", response.product.name);
        });
    })
    updatedItems.removed.clear();
}

window.onload = function () {
    cart.getAllItems(0,10,renderItems);

    const updateCartBtn = document.getElementById("update-cart-btn");
    updateCartBtn.addEventListener("click", updateCart)
}