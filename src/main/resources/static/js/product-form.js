const MAX_FILE_SIZE = 4; // in MB

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
        for (let img of uploader.files) {
            if (img.size > MAX_FILE_SIZE * 2**20) {
                previewUploadArea.innerHTML = "";
                uploader.files = null;
                alert(`Max file size for image: ${MAX_FILE_SIZE}MB`);
                break;
            }
            previewUploadArea.appendChild(makePreviewEle(img));
        }
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