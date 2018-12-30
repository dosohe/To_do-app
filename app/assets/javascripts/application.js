// This is a manifest file that'll be compiled into application.js, which will include all the files
// listed below.
//
// Any JavaScript/Coffee file within this directory, lib/assets/javascripts, or any plugin's
// vendor/assets/javascripts directory can be referenced here using a relative path.
//
// It's not advisable to add code directly here, but if you do, it'll appear at the bottom of the
// compiled file. JavaScript code in this file should be added after the last require_* statement.
//
// Read Sprockets README (https://github.com/rails/sprockets#sprockets-directives) for details
// about supported directives.
//
//= require rails-ujs
//= require activestorage
//= require jquery
//= require_tree .



$(document).ready(function(){

  var sub = false;

  $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
  });

  $('input').on('ifChecked', function(event){
      $(this).closest("form").css('text-decoration-line', 'line-through');
      if (sub) {
          $(this).closest("form").submit();
      }
  });

  $('input').on('ifUnchecked', function (event) {
      $(this).closest("form").css('text-decoration-line', 'initial');
      if (sub) {
          $(this).closest("form").submit();
      }
  });

  $.each(get, function (index, num) {
     $("#todo"+num).iCheck('check');
  });
  sub = true;

  $("#btn-s").click(function(event) {
  event.preventDefault();
  $('#fo').submit();
  $('#myModal').hide();
  // alert('click');
});

    $('#submit_form').hide();
    $("#add_todo").click( function() {
    event.preventDefault();
    $('#submit_form').show();
    $('#myModal').show();
    $('select').prepend('<option selected></option>').select2({
      placeholder: "Категория",
      minimumResultsForSearch: -1,
      // 'allowClear': true
        // allowClear: true
    });
  });

   $("#btn-c").click( function() {
    event.preventDefault();
    $('#submit_form').hide();
    $('#myModal').hide();
    // alert('click');
  });




});
