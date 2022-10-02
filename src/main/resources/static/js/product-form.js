const makePreviewEle = function(imgFile) {
    let ctn = document.createElement("div");
    ctn.classList.add("img-container");

    let img = document.createElement("img");
    img.src = URL.createObjectURL(imgFile);
    img.onload = () => URL.revokeObjectURL(img.src);

    ctn.appendChild(img);
    return ctn;
}

window.onload = function() {
    const previewUploadArea = document.getElementById("upload-previews");
    const uploader = document.getElementById("images");

    uploader.onchange = function () {
        previewUploadArea.innerHTML = "";
        for (let img of uploader.files)
            previewUploadArea.appendChild(makePreviewEle(img));
    }

    const productForm = document.getElementById("product-form");
    productForm.addEventListener("submit", function (e) {
        e.preventDefault();
        if (uploader.files.length === 0) {
            uploader.files = null;
        }
        this.submit();
    })
}