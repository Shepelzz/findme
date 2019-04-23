var RestService = (function () {
    return {
        save: function (_restUrl, data, successCallback, errorCallback) {
            $.ajax({
                type: "POST",
                url: _restUrl,
                contentType: "application/json",
                data: JSON.stringify(data)
            }).done(function (result) {
                successCallback(result);//successCallback ? successCallback(result) : table.ajax.reload(null, false);
            }).fail(function (error) {
                errorCallback(error);//errorCallback ? errorCallback(error) : AlertService.processRequestError(error);
            });
        },
        update: function (_restUrl, data, successCallback, errorCallback) {
            $.ajax({
                type: "PUT",
                url: _restUrl,
                contentType: "application/json",
                data: JSON.stringify(data)
            }).done(function (result) {
                successCallback(result);
            }).fail(function (error) {
                errorCallback(error);
            });
        },
        deleteById: function (_restUrl, id, successCallback, errorCallback) {
            AlertService.askQuestion("Are you sure want to delete this element?", "Caution", function (answerYes) {
                if (answerYes) {
                    $.ajax({
                        type: "DELETE",
                        url: _restUrl + "/" + id
                    }).done(function (result) {
                        successCallback(result);
                    }).fail(function (error) {
                        errorCallback(error);
                    });
                }
            });
        },
        getById: function (_restUrl, id, successCallback, errorCallback) {
            $.ajax({
                type: "GET",
                url: _restUrl + "/" + id
            }).done(function (result) {
                successCallback(result);
            }).fail(function (error) {
                errorCallback(error);
            });
        },
        get: function (_restUrl, successCallback, errorCallback) {
            $.ajax({
                type: "GET",
                url: _restUrl
            }).done(function (result) {
                successCallback(result);
            }).fail(function (error) {
                errorCallback(error);
            });
        }

    }
}());

