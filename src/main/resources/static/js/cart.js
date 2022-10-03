const getAllItems = function(page, size, callback) {
    let xhr = new XMLHttpRequest();
    xhr.open("GET", `/api/v1/carts?page=${page}&size=${size}`);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            switch (this.status) {
                case 200:
                    callback(JSON.parse(this.response));
                    break;
                case 401:
                    location.href = "/login";
                    break;
                default:
                    alert("Something went wrong, check console for more details");
                    console.log(this.response);
            }
        }
    }

    xhr.send();
}

const addToCart = function(item, callback) {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", `/api/v1/carts/`);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            switch (this.status) {
                case 200:
                    callback(JSON.parse(this.response));
                    break;
                case 401:
                    location.href = "/login";
                    break;
                default:
                    alert("Something went wrong, check console for more details");
                    console.log(this.response);
            }
        }
    }

    xhr.send(JSON.stringify(item))
}

const updateItem = function(item, callback) {
    let xhr = new XMLHttpRequest();
    xhr.open("PUT", `/api/v1/carts/${item.id}`);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            switch (this.status) {
                case 200:
                    callback(JSON.parse(this.response));
                    break;
                case 401:
                    location.href = "/login";
                    break;
                default:
                    alert("Something went wrong, check console for more details");
                    console.log(this.response);
            }
        }
    }

    xhr.send(JSON.stringify(
        {
            "quantity": item.quantity
        }
    ))
}

const deleteItem = function(itemId, callback) {
    let xhr = new XMLHttpRequest();
    xhr.open("DELETE", `/api/v1/carts/${itemId}`);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            switch (this.status) {
                case 200:
                    callback(JSON.parse(this.response));
                    break;
                case 401:
                    location.href = "/login";
                    break;
                default:
                    alert("Something went wrong, check console for more details");
                    console.log(this.response);
            }
        }
    }

    xhr.send();
}

const cart = {
    getAllItems : getAllItems,
    addToCart : addToCart,
    updateItem : updateItem,
    deleteItem : deleteItem
}

export default cart;