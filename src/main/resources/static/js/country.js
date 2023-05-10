$(document).ready(function () {
  $.ajax({
    method: "GET",
    url: "/api/region",
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      let x;
      $.map(res, function (reg, key) {
        x += `<option value="${reg.id}">${reg.name}</option>`;
      });
      $(".select_region").append(x);
    },
    error: (err) => {
      console.log(err);
    },
  });

  $("#table-country").DataTable({
    ajax: {
      url: "/api/country",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: function (data, type, row, meta) {
          return meta.row + 1;
        },
      },
      { data: "code" },
      { data: "name" },
      { data: "region.name" },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
      <button type="button"
      class="btn btn-primary"
      data-bs-toggle="modal"
      data-bs-target="#detailCountry"
      onClick="getById(${data.id})">
      Detail
      </button>

      <button 
      type="button" 
      class="btn btn-warning" 
      data-bs-toggle="modal" 
      data-bs-target="#editTheCountry" 
      onClick="beforeUpdate(${data.id})">
      Edit
      </button>

      <button
      class="btn btn-danger"
      onClick="deleteCountry(${data.id})">
      Delete
      </button>
      `;
        },
      },
    ],
  });
});

function create() {
  let newCode = $("#create_code").val();
  let newCountry = $("#create_name").val();
  let regId = $(".select_region").val();

  $.ajax({
    method: "POST",
    url: "/api/country",
    dataType: "JSON",
    data: JSON.stringify({
      code: newCode,
      name: newCountry,
      region: { id: regId },
    }),
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    success: (res) => {
      $("#addCountry").modal("hide");
      $("#table-country").DataTable().ajax.reload();
      $("#create_code").val("");
      $("#create_name").val("");
      $(".select_region").val("");
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Your country has been saved",
        showConfirmButton: false,
        timer: 1500,
      });
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function getById(id) {
  $.ajax({
    method: "GET",
    url: "/api/country/" + id,
    dataType: "JSON",
    success: (res) => {
      $("#detail_code").val(res.code);
      $("#detail_name").val(res.name);
      $("#detail_region_name").val(res.region.name);
    },
  });
}

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: "/api/country/" + id,
    dataType: "JSON",
    success: (res) => {
      $("#update_code").val(res.code);
      $("#update_name").val(res.name);
      $(".select_region").val(res.region.id);
      $("#update_id").val(res.id);
    },
  });
}

function update() {
  let newCode = $("#update_code").val();
  let newCountry = $("#update_name").val();
  let regId = $("#select_region").val();
  let idUpdte = $("#update_id").val();

  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, update it!",
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire("Updated!", "Region success to update...", "success");
      $.ajax({
        method: "PUT",
        url: "/api/country/" + idUpdte,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        data: JSON.stringify({
          code: newCode,
          name: newCountry,
          region: { id: regId },
        }),
        contentType: "application/json",
        success: (res) => {
          $("#editTheCountry").modal("hide");
          $("#table-country").DataTable().ajax.reload();
          $("#update_code").val("");
          $("#update_name").val("");
          $("#select_region").val("");
        },
      });
    }
  });
}

function deleteCountry(id) {
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success mx-2",
      cancelButton: "btn btn-danger mx-2",
    },
    buttonsStyling: false,
  });

  swalWithBootstrapButtons
    .fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirm",
      cancelButtonText: "Cancel",
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        swalWithBootstrapButtons.fire(
          "Deleted!",
          "Country success to delete!!!",
          "success"
        );
        $.ajax({
          method: "DELETE",
          url: "/api/country/" + id,
          beforeSend: addCsrfToken(),
          dataType: "JSON",
          success: (res) => {
            $("#table-country").DataTable().ajax.reload();
          },
        });
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          "Cancelled",
          "Country data is safe :)",
          "error"
        );
      }
    });
}