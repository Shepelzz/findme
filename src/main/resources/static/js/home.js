
// user delete by id
function userDelete(id) {
    if(confirm('Remove user with id: '+id+'?')) {
        loader.show();
        $.ajax({
            type: "DELETE",
            url: "remove-user/" + id,
            // async: false,
            success: function success() {
                debugger;
                loader.hide();
                alert('User removed successfully');
                window.location.reload(true);
            },
            error: function (xhr) {
                debugger;
                loader.hide();
                alert(xhr.responseText);
            }
        });
    }
}
