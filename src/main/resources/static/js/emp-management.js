import ajaxRequest from "/js/modules/ajaxrequest.js";

const filters = document.getElementById("filters");

const getFilterParams = function() {
    let paramSequence = "";

    let specifiedRoles = [];
    filters.querySelectorAll(".role").forEach(r => {
        if (r.checked) {
            specifiedRoles.push(r.value);
        }
    })
    if (specifiedRoles.length > 0) {
        paramSequence += "authorities=" + specifiedRoles.join(",");
    }

    return paramSequence;
}

const reloadEmp = function () {
    location.href = `/manage/employees?${getFilterParams()}`;
}

const addAuthority = function (userId, authorityId) {
    ajaxRequest.post(`/api/v1/users/${userId}/authorities`, {"id": authorityId}, () => {alert("Added")});
}

const removeAuthority = function (userId, authorityId) {
    ajaxRequest.delete(`/api/v1/users/${userId}/authorities/${authorityId}`, null, () => {alert("Removed")});
}

window.onload = function() {

    const roleFilter = filters.querySelectorAll(".role");
    roleFilter.forEach(r => {
        r.addEventListener("change", reloadEmp);
    })

    const userRole = document.querySelectorAll("#emp-list-container .emp .role");
    userRole.forEach(r => {
        r.addEventListener("change", function() {
            if (this.checked) {
                addAuthority(this.getAttribute("data-for-user"), this.value);
            } else {
                removeAuthority(this.getAttribute("data-for-user"), this.value);
            }
        })
    })
}