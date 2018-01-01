var expander = function () {
    $(".att-collapse-header").click(function () {
        var $params = $(this).next();
        $(".att-collapse-body").slideUp("fast");
        if (!($params.is(":visible"))) {
            $params.slideToggle(500);
        }
    })
};
$(document).ready(expander);


$(document).ready(function () {
    $(".test").click(expander)
});
$('.card-panel.r').contents().filter(function () {
    return this.nodeType === 3
}).each(function () {
    this.textContent = this.textContent.replace("Test", "Step").replace("Suite", "Test");
});

$('.card-panel .panel-name').contents().filter(function () {
    return this.nodeType === 3
}).each(function () {
    this.textContent = this.textContent.replace("Test", "Step").replace("Suite", "Test");
});

$('.card-panel .text-small').contents().filter(function () {
    return this.nodeType === 3 || this.nodeType === 1
}).each(function () {
    this.textContent = this.textContent.replace("test", "step").replace("suite", "test");
});


$(".view-summary h5").text(function () {
    return $(this).text().replace("Suite", "Test");
});