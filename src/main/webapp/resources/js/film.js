$(document).ready(function(){

    var jurassic = 'https://www.youtube.com/embed/qoCl9KcItWY';
    var astral = 'https://www.youtube.com/embed/3KEmPmijois';
    var spy = 'https://www.youtube.com/embed/ltijEmlyqlg';
    var insideOut = 'https://www.youtube.com/embed/_MC3XuMvsDI';

    embedVideo();

    function embedVideo() {
        switch($('.film-name').text()) {
            case 'jurassic-world':
                $('iframe').attr('src', jurassic);
                break;
            case 'insidious: chapter 3':
                $('iframe').attr('src', astral);
                break;
            case 'spy':
                $('iframe').attr('src', spy);
                break;
            case 'inside out':
                $('iframe').attr('src', insideOut);
                break;
        }
    }

});