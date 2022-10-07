import review from "/js/modules/review.js";

window.onload = function () {
    let orderItems = document.getElementsByClassName("item");

    for (let orderItem of orderItems) {
        let publishReviewBtn = orderItem.querySelector(".publish-btn");

        publishReviewBtn.addEventListener("click", function () {
            const orderItemId = Number(orderItem.getAttribute("data-item-id"));
            const rating = Number(orderItem.querySelector(".rating input[type='number']").value);
            const comment = orderItem.querySelector(".comment textarea").value;

            const callback = function(success, res) {
                if (success) alert("Published");
                else alert("Couldn't publish this review");
            }
            if (this.getAttribute("data-is-new") === "true") {
                // add review
                review.add(orderItemId, rating, comment, callback);
            } else {
                // update review
                review.update(Number(this.getAttribute("data-review-id")), rating, comment, callback);
            }
        })
    }
}