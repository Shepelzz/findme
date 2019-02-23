function dropDownMenu(id, activeTheme) {
    let x = document.getElementById(id);
    if (x.className.indexOf("w3-show") === -1) {
        x.className += " w3-show";
        if(activeTheme)
            x.previousElementSibling.className += " w3-theme-d1";
    } else {
        x.className = x.className.replace("w3-show", "");
        x.previousElementSibling.className =
            x.previousElementSibling.className.replace(" w3-theme-d1", "");
    }
}


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
                let postInfo = "<div>\n" +
                    "                <div class=\"w3-container w3-white w3-margin\" style=\"position: relative\">\n" +
                    "                    <i class=\"fa fa-bars w3-button w3-text-theme w3-padding-large\" style=\"position: absolute; right: 0\"></i>\n" +
                    "                    <div class=\"post-menu w3-hide\">\n" +
                    "                        <div class=\"post-menu-item w3-opacity\">Edit post</div>\n" +
                    "                        <div th:onclick=\"|postDelete('${post.getId()}')|\" class=\"post-menu-item w3-opacity\">Delete post</div>\n" +
                    "                    </div>\n" +
                    "                    <div>\n" +
                    "                        <br>\n" +
                    "                        <img src=\"http://agarioskins.com/submitted/useruploads/Thug%20Cat.png\" alt=\"Avatar\" class=\"w3-left w3-circle w3-margin-right\" style=\"width:60px\">\n" +
                    "                        <span class=\"w3-right w3-opacity\" style=\"margin-right: 50px\">"+parsePostDate(new Date(post.datePosted))+"</span>\n" +
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

function ajaxSavePost() {
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