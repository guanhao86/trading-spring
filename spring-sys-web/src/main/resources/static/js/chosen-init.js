/**
 * Created by spink on 2017/4/11.
 */
/*下拉列表初始化样式信息，支持搜索*/
$(function () {
    var config = {
        ".chosen-select": {},
        ".chosen-select-deselect": {allow_single_deselect: !0},
        ".chosen-select-no-single": {disable_search_threshold: 10},
        ".chosen-select-no-results": {no_results_text: "Oops, nothing found!"},
        ".chosen-select-width": {width: "100%"}
    };
    $(".chosen-container").css("width","100%");
    for (var selector in config)$(selector).chosen(config[selector]);
})

var chosenInfo = {
    getChosen: function () {
        var config = {
            ".chosen-select": {},
            ".chosen-select-deselect": {allow_single_deselect: !0},
            ".chosen-select-no-single": {disable_search_threshold: 10},
            ".chosen-select-no-results": {no_results_text: "Oops, nothing found!"},
            ".chosen-select-width": {width: "100%"}
        };
        $(".chosen-container").css("width","100%");
        for (var selector in config)$(selector).chosen(config[selector]);
    },
    updateChose: function (name) {
        $('select[name=' + name + ']').trigger("chosen:updated.chosen");
    },
    getMultipleChosen: function () {
        var config = {
            ".chosen-select": {},
            ".chosen-select-deselect": {allow_single_deselect: true},
            ".chosen-select-no-single": {disable_search_threshold: 10},
            ".chosen-select-no-results": {no_results_text: "Oops, nothing found!"},
            ".chosen-select-width": {width: "100%"}
        };
        $(".chosen-container").css("width","100%");
        for (var selector in config)$(selector).chosen(config[selector]);
    }
}