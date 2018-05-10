$(document).ready(function () {
    $('.table-on tr').on('click', function () {
        var url = $(this).attr("href");
        window.location.href = "https://localhost:8181" + url;
    })
    // test();
});

// function test() {
//     var company = document.URL.split("/")[4];
//
//     //console.log(company);
//     $('.module-circle').each(function () {
//         var moduleId = $(this).data("id");
//         var ting = $(this);
//         //console.log("api/interface", {module: moduleId, company: company})
//
//         $.getJSON("/api/interface", {module: moduleId, company: company},
//             function (data) {
//                 console.log(this);
//                 $(ting).find(".num").text(data);
//             }
//         );
//     });
// }