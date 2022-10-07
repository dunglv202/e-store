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

const createPreviewImageOption = function(image) {
    let ctn = document.createElement("div");
    ctn.classList.add("img-container");

    ctn.innerHTML = `
        <img src="/${image.path}">
    `;

    ctn.addEventListener("click", function() {
        // change thumbnail display
        document.getElementById("preview").querySelector("img").src = `/${image.path}`;
        // update preview field
        document.getElementById("product-preview").value = image.path;
        // close panel
        document.getElementById("preview-selector-panel").style.display = "none";
    })

    return ctn;
}

const renderPreviewSelections = function (images) {
    const previewSelectionsContainer = document.getElementById("preview-selections");
    for (let image of images) {
        previewSelectionsContainer.appendChild(createPreviewImageOption(image));
    }
}

const loadImage = function () {
    productService.getImages(productId, imagePage, IMAGE_PER_LOAD, renderImages);
    imagePage++;
}

let previewPage = 0;
const loadPreviewSelections = function () {
    productService.getImages(productId, previewPage, IMAGE_PER_LOAD, function(images) {
        if (images.length > 0) {
            renderPreviewSelections(images);
            previewPage++;
        }
    })
}

window.addEventListener("load", function() {
    loadImage();

    const moreImagesBtn = document.getElementById("more-image-btn");
    moreImagesBtn.addEventListener("click", (e) => {
        e.preventDefault();
        loadImage();
    });

    const closeableEles = document.getElementsByClassName("closeable");
    for (let closeableEle of closeableEles) {
        let closeBtn = closeableEle.querySelector(".close-btn");
        closeBtn.addEventListener("click", function() {
            closeableEle.style.display = "none";
        })
    }

    const preview = document.getElementById("preview");
    const previewSelectorPanel = document.getElementById("preview-selector-panel");
    preview.addEventListener("click", function() {
        previewSelectorPanel.style.display = "block";
        loadPreviewSelections();
    })

    previewSelectorPanel.querySelector(".more-btn").addEventListener("click", loadPreviewSelections);
})