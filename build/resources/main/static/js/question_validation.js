$(Document).ready(function () {
    var quality = null;
    var importance = null;
    var json = null;
    var pagenum = 0;

    $.ajax({//send the AJAX request
        type: "GET",
        url: "/api/questionnaire?id=" + getUrlParameter("q"),
        dataType: "json",
        cache: false,
        timeout: 600000
        , async: true
    })
        .done(function (data) { //what to do if the request is a success.
            json = data;
            $('#question').text(data[pagenum].question);
            $('#progress').attr("value", pagenum);
            $('#progress').attr("max", json.length);
        })
        .fail(function (jqXHR, textStatus, errorThrown) { //and what to do if it fails
            console.log("failed");
        });

    //This is for validaiton, if something is valid then we will call these methods which
    //submit the scores.

    $("#scoreForm").submit(function (e) {
        $('#error').hide();

        if ($('#comment').prop("disabled") == true && $('input:checkbox:checked').length == 2) {
            submitScore();
            style();
        } else if ($('#comment').prop("disabled") == false && $('input:checkbox:checked').length == 2 && $('#comment').val().length >= 10) {
            submitScore();
            style();
        } else {
            $('#error').show();
        }

        e.preventDefault();
    });


    $('.checkbox-quality').on('change', function () {
        $('.checkbox-quality').not(this).prop('checked', false);
        quality = this;
        check()
    });

    $('.checkbox-importance').on('change', function () {
        $('.checkbox-importance').not(this).prop('checked', false);
        importance = this;
        check()
    });

    function check() {
        $('#comment').attr('disabled', true);

        if ($(quality) != null)
            if ($(quality).is(':checked'))
                if ($(quality).attr('value') == 1 || $(quality).attr('value') == 5) $('#comment').attr('disabled', false);

        if ($(importance) != null)
            if ($(importance).is(':checked'))
                if ($(importance).attr('value') == 1 || $(importance).attr('value') == 5) $('#comment').attr('disabled', false);

    }

    //When the button is pressed, this code will run to submit an answer into the db
    function submitScore() {
        var num_list = 0;
        var total = 0;

        console.log(json[pagenum].id);

        $('input:checkbox:checked').each(function () {
            var check = parseInt($(this).val());
            total += check;
            num_list++;
        });

        var scoreForm = {
            "score": Math.round(total / num_list),
            "comment": $('#comment').val(),
            "fkQuestion": json[pagenum].id,
            "fkModule": getUrlParameter("f"),
            "num": null
        };

        $.ajax({
            type: "POST",
            url: "/api/questionnaire/question",
            data: scoreForm,
            encode: true
        }).done(function (data) { //what to do if the request is a success.
            //if the submission is successful, it will increase the pagenum variable by one
            //which is used to get the question.
            pagenum++;
           $('#progress').attr("value", pagenum);
            //This is how you get the next question
            var requested = json[pagenum].question;
            //setting the next question
            $('#question').text(requested);
            $('#comment').val("");
            $('input:checkbox').prop('checked', false);
        }).fail(function (jqXHR, textStatus, errorThrown) { //and what to do if it fails
            console.log("error");
        });
    }

    function style() {
        //after the last question, this will redirect you to the dashboard
        if (pagenum == json.length - 1){
            window.location.href = "http://localhost:8080/dashboard";
            // this if else statement changes from submit to finish button
        } else if (pagenum == json.length - 2){
            $('#question-next').html("Finish<i class='fa fa-flag' aria-hidden='true'></i>");
        }
    }

    $('#table-module-add').on('click', function () {
        $('#module-modal').show();
    })
});
