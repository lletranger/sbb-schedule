$( document ).ready(function() {
    $('.firstfour').change(function() {
        $('.cardImg').removeClass('active');
        var card = $('.firstfour').val();
        var firstNumber = card.substr(0,1);
        switch(firstNumber) {
            case '4': $('.visa').addClass('active');
                break;
            case '5': $('.mastercard').addClass('active');
                break;
        }
    });
    $('.ch-box').prop('checked','true');
    $('.ch-box').attr('checked','true');

});

