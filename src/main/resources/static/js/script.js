
$(function () {
    $('a[href*=#]:not([href=#])').click(function () {
        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: (target.offset().top - 80) // adjust this according to your content
                }, 700); // scrolling speed
                return false;
            }
        }
    });
});

(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/pl_PL/sdk.js#xfbml=1&version=v2.8";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

$(document).ready(function()
{
    // Show tooltip on all <a/> elements with title attributes, but only when
    // clicked. Clicking anywhere else on the document will hide the tooltip
    $('span[title]').qtip({
        show: {
            event:'click',
            //show:'click',
            effect: function () {
                $(this).slideDown();
            }
        },
        hide: {
            event:'click',
            //hide:'unfocus',
            effect:function () {
                $(this).slideUp();

            }
        },
        position:{
            my:'top right',
            at:'bottom left'
        },
        style:{
            classes:'qtip-jtools'
        }




    })
});


