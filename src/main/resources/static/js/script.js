
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

//Info about
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
            my:'top center',
            at:'bottom center'
        },
        style:{
            classes:'qtip-jtools'
        }




    })
});
//Redirect QRcode
$(document).ready(function()
{

    $('.qrcode').each(function () {
        $(this).qtip({
            content:{
                title:"Przekierowanie",
                text:'<img src="'+$(this).attr('title')+'" width="250" height="250"/>'

            },
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

    });

//Subscribe
$(document).ready(function()
{

    $('.subscribe').each(function () {
        $(this).qtip({
            content:{
                title:"Subskrypcja",
                text:'<img src="'+$(this).attr('title')+'" width="250" height="250"/>' +
                ''

            },
            show: {
                event:'click',
                //show:'click',
                ready:true,
                modal:{
                    on:click,
                    blur:false
                },
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
                my:'top left',
                at:'bottom right'
            },
            style:{
                classes:'qtip-jtools'
            }




        })
    });

});

function dialogue(content, title) {
    $('.subscribe').qtip({
        content: {
            text: content,
            title: title
        },
        position: {
            my: 'center', at: 'center',
            target: $(window)
        },
        show: {
            ready: true,
            modal: {
                on: true,
                blur: false
            }
        },
        hide: {
            event:'false',
            //hide:'unfocus',
            effect:function () {
                $(this).slideUp();

            }
        },
        style:{
            classes:'qtip-jtools'
        },
        events: {
            render: function(event, api) {
                $('button', api.elements.content).click(function(e) {
                    api.hide(e);
                });
            },
            hide: function(event, api) { api.destroy(); }
        }
    });
}
window.Prompt = function() {
    var message = $('<p />', { text: 'What do you think about these custom dialogues?' }),
        input = $('<input />', { val: 'Fantastic!' }),
        ok = $('<button />', {
            text: 'Ok'
        }),
        cancel = $('<button />', {
            text: 'Cancel'
        });

    dialogue( message.add(input).add(ok).add(cancel), 'Attention!' );
}

