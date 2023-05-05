$(document).ready(function () {
  $("#table-region").DataTable({
    ajax: {
      url: "/api/region",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: function (data, type, row, meta) {
          return meta.row + 1;
        },
      },
      { data: "name" },
      {
        data: null,
        render: (data, type, row, meta) => {
          return `
        <button 
        type="button" 
        class="btn btn-primary" 
        data-bs-toggle="modal" 
        data-bs-target="#detailRegion" 
        onClick="getById(${data.id})">
        Detail
        </button>
        
        <button 
        type="button" 
        class="btn btn-warning" 
        data-bs-toggle="modal" 
        data-bs-target="#updateRegion" 
        onClick="beforeUpdate(${data.id})">
        Edit
        </button>

        <button 
        class="btn btn-danger" 
        onClick="deleteRegion(${data.id})">
        Delete
        </button>
        `;
        },
      },
    ],
  });
});

function create() {
  let newReg = $("#create_name").val();

  $.ajax({
    method: "POST",
    url: "/api/region",
    dataType: "JSON",
    data: JSON.stringify({
      name: newReg,
    }),
    contentType: "application/json",
    success: (res) => {
      $("#addRegion").modal("hide");
      $("#table-region").DataTable().ajax.reload();
      $("#create_name").val("");

      Swal.fire({
        position: "center",
        icon: "success",
        title: "Region success to create...",
        showConfirmButton: false,
        timer: 1500,
      });
    },
  });
}

function getById(id) {
  $.ajax({
    method: "GET",
    url: "/api/region/" + id,
    dataType: "JSON",
    success: (res) => {
      $("#detail_name").val(res.name);
    },
  });
}

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: "/api/region/" + id,
    dataType: "JSON",
    success: (res) => {
      $("#update_name").val(res.name);
      $("#update_id").val(res.id);
    },
  });
}

function update() {
  let nameUpdate = $("#update_name").val();
  let idReg = $("#update_id").val();

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
      $.ajax({
        method: "PUT",
        url: "/api/region/" + idReg,
        dataType: "JSON",
        data: JSON.stringify({
          name: nameUpdate,
        }),
        contentType: "application/json",
        success: (res) => {
          $("#updateRegion").modal("hide");
          $("#table-region").DataTable().ajax.reload();
          $("#update_name").val("");
        },
      });
      Swal.fire("Updated!", "Region success to update...", "success");
    }
  });
}

function deleteRegion(id) {
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
          "Region success to delete!!!",
          "success"
        );
        $.ajax({
          method: "DELETE",
          url: "/api/region/" + id,
          dataType: "JSON",
          success: (res) => {
            $("#table-region").DataTable().ajax.reload();
          },
        });
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        swalWithBootstrapButtons.fire(
          "Cancelled",
          "Region data is safe :)",
          "error"
        );
      }
    });
}
