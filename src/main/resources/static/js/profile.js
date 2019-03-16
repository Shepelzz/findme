

function dropDownMenu(id, activeTheme) {
    let x = document.getElementById(id);
    if (x.className.indexOf("w3-show") === -1) {
        x.className += " w3-show";
        if(activeTheme)
            x.previousElementSibling.className += " w3-theme-d1";
        openedMenu = id;
    } else {
        x.className = x.className.replace("w3-show", "");
        x.previousElementSibling.className =
            x.previousElementSibling.className.replace(" w3-theme-d1", "");
        openedMenu = null;
    }
}

// $(document).mouseup(function(e) {
//
// });

function ajaxFilterPost(){
    // DO GET
    $.ajax({
        type : "GET",
        url : "/get-filtered-posts",
        data : {
            ownerPosts : $('#ownerPosts').prop('checked'),
            friendsPosts: $('#friendsPosts').prop('checked'),
            userPostedId: $("#userPostedId").val(),
            userId: userId
        },
        success : function(result) {
            $('#getResultDiv div').empty();
            $.each(result, function(i, post){
                let postMenu =
                    "<i id='postMenuButton' onclick=\"dropDownMenu('postMenu"+post.id+"',false)\" class=\"fa fa-bars w3-button w3-text-theme w3-padding-large\" style=\"position: absolute; right: 0\"></i>\n" +
                    "<div id='postMenu"+post.id+"' class=\"post-menu w3-hide exit-on-keyDown\">\n" +
                    "   <button class=\"w3-button w3-block w3-theme-l5 w3-left-align\" onclick=\"alert("+post.id+")\">Show id</button>\n";
                postMenu += (post.userPosted.id !== userLoggedId && post.userPagePosted.id !== userLoggedId) ? "<button class=\"w3-button w3-block w3-theme-l5 w3-left-align\" onclick=\"alert('repost')\">Repost</button>\n" : "";
                postMenu += (post.userPosted.id === userLoggedId || post.userPagePosted.id === userLoggedId) ? "<button class=\"w3-button w3-block w3-theme-l5 w3-left-align\" onclick=\"postDelete("+post.id+")\">Remove</button>\n" : "";
                postMenu +="</div>";

                // debugger;
                let postInfo = "<div>\n" +
                    "                <div class=\"w3-container w3-white w3-margin\" style=\"position: relative\">\n" +
                                postMenu +
                    "                    <div>\n" +
                    "                        <br>\n" +
                    "                        <a href=\"/user/"+post.userPosted.id+"\">\n" +
                    "                            <img src=\"http://agarioskins.com/submitted/useruploads/Thug%20Cat.png\" alt=\"Avatar\" class=\"avatar avatar-s1 w3-left w3-circle w3-margin-right\">\n" +
                    "                        </a>" +
                    "                        <span class=\"w3-right w3-opacity\" style=\"margin-right: 50px\">"+parseDateSent(new Date(post.datePosted))+"</span>\n" +
                    "                        <h4 class=\"inline\">" + post.userPosted.firstName + " " + post.userPosted.lastName.substring(0, 1) + ".</h4>\n" +
                    "                            <p class=\"inline w3-opacity w3-margin-left\">"+parsePostTaggedUsersAndLocation(post.usersTagged, post.location)+"</p><br>\n" +
                    "                        <hr class=\"w3-clear\">\n" +
                    "                        <p>" + post.message + "</p>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>";
                $('#getResultDiv .list-group').append(postInfo)
            });
        },
        error : function(e) {
            $("#getResultDiv").html("<strong>Error</strong>");
            console.log("ERROR: "+ e);
        }
    });
}

function postSave() {
    $.ajax({
        type: "POST",
        url: "/save-post",
        data: $('#save_post_form').serialize(),
        success:function success() {
            window.location.reload(true);
        },
        error: function(xhr) {
            alert(xhr.responseText);
        }
    });
}

function postDelete(postId){
    if (confirm("Do you want delete this post?")) {
        $.ajax({
            type: "DELETE",
            url: "/delete-post?postId="+postId,
            success: function success() {
                window.location.reload(true);
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    }
}

function requestSave(userId) {
    debugger;
    $.ajax({

        type: "POST",
        url: "/save-relationship",
        data: {"userId": userId},
        success: function success() {
            window.location.reload(true);
        },
        error: function(xhr) {
            alert(xhr.responseText);
        }
    });
}

function requestUpdate(userId, status) {
    debugger;
    $.ajax({
        type: "POST",
        url: "/update-relationship",
        data: {"userId": userId, "status": status},
        success: function success() {
            window.location.reload(true);
        },
        error: function(xhr) {
            alert(xhr.responseText);
        }
    });
}