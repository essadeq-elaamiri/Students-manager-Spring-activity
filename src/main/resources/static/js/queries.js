console.log("detected")
const deleteStudentBtn = document.querySelectorAll(".deleteStudentBtn");
deleteStudentBtn.forEach(btn => {
    btn.addEventListener('click', event=>{
        event.preventDefault();
        var deleteLink = btn.getAttribute("data-deleteLink");
        console.log(btn);
        if(confirm("Are you sure? want delete ?")){
            //location.href = deleteLink;
            fetch(deleteLink, {
                method: "DELETE"
            }).then(success=>{
                alert("Deleted !");
                //reload
                location.reload();
            }).then(err=>{
                console.log(err);
            })
        }
    })
})