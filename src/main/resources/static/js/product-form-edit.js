import productService from "./modules/product.js";

const IMAGE_PER_LOAD = 12;
let imagePage = 0;

const createImageEle = function (image) {
    let ctn = document.createElement("div");
    ctn.classList.add("img-container", "deletable", "hidden");

    let img = document.createElement("img");
    img.src = `/${image.path}`;

    img.onload = function() {
        ctn.classList.remove("hidden");
    }

    ctn.appendChild(img);
    return ctn;
}

const renderImages = function (images) {
    const imageArea = document.getElementById("upload-previews");

    for (let image of images) {
        let ctn = createImageEle(image);
        ctn.addEventListener("click", function() {
            productService.deleteImage(productId, image.id, (response) => {
                this.remove();
                alert("Deleted!");
            });
        });
        imageArea.appendChild(ctn);
    }
}

const loadImage = function () {
    productService.getImages(productId, imagePage, IMAGE_PER_LOAD, renderImages);
    imagePage++;
}

window.addEventListener("load", function() {
    loadImage();

    const moreImagesBtn = document.getElementById("more-image-btn");
    moreImagesBtn.addEventListener("click", (e) => {
        e.preventDefault();
        loadImage();
    });
})