$(document).ready(function() {
    if ($('.generic-pop-up .pop-up-content').length > 0) {
        $('.pop-up-content').css('top', "calc( 45% - " + $('.pop-up-content').innerHeight() / 2 + "px)");
    }
});