import review from "/js/modules/review.js";
import cart from "/js/cart.js";

let curReviewPage = 0;
let REVIEW_PER_LOAD = 10;

const createReviewEle = function(review) {
    let ctn = document.createElement("li");
    ctn.classList.add("review");
    let template =
        `
            <div class="review-info">
                <div class="username">${review.user}</div>
                <div class="rating">${review.rating}/10</div>
            </div>
            <div class="date">
                ${new Date(review.dateCreated).toLocaleString()}
            </div>
            <div class="comment">
                ${review.comment}
            </div>
        `;

    ctn.innerHTML = template;
    return ctn;
}

const renderReviews = function(reviews) {
    const reviewContainer = document.getElementById("review-container");
    reviewContainer.innerHTML = "";
    for (let review of reviews) {
        reviewContainer.appendChild(createReviewEle(review));
    }
}

const loadReviews = function (direction) {
    // try to load reviews
    review.getAll(productId, curReviewPage+direction, REVIEW_PER_LOAD, renderReviews);
}

const initTabs = function () {
    let tabs = document.getElementsByClassName("tab");

    for (let tab of tabs) {
        tab.addEventListener("click", function() {
            // change active tab
            this.parentElement.querySelector(".active").classList.remove("active");
            this.classList.add("active");

            // show tab content
            let tabContent = this.parentElement.nextElementSibling;
            tabContent.querySelector(".active").classList.remove("active");
            tabContent.querySelector(`.${this.classList[0]}`).classList.add("active");
        })
    }
}

window.addEventListener("load", function () {
    initTabs();
    loadReviews(0);
    document.getElementById("add-to-cart-btn").addEventListener("click", function() {
        cart.addToCart({
            "product": {
                "id": productId
            },
            "quantity": Number(document.getElementById("quantity").value)
        }, (item) => {
            alert("Added " + item.product.name + " to cart");
        });
    })

    let imagesContainer = document.getElementById("images");
    let allImageEles = imagesContainer.querySelectorAll(".img-container img");
    let previewEle = document.getElementById("preview-img");

    for (let image of allImageEles) {
        image.addEventListener("click", function() {
            imagesContainer.querySelector(".active").classList.remove("active");

            // change preview
            this.parentElement.classList.add("active");
            previewEle.src = this.src;
        })
    }
})