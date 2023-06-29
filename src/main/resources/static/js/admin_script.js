const url = "/api/admin"

async function adminPage() {
    let page = await fetch(url)
    if (page.ok) {
        let listUsers = await page.json();
        table(listUsers)
    } else {
        alert(`Error, ${page.status}`)
    }
}

function table(listUsers) {
    const table_body = document.getElementById("table_body")
    let HTMLData = ""
    for (let user of listUsers) {
        let roles = []
        for (let role of user.roles) {
            roles.push(" " + role.role.toString().substring(5))
        }

        HTMLData += `<tr> 
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${roles.join(" ")}</td>
                <td>
                    <button class="btn btn-primary" id="table-cell-edit-button-1" data-toggle="modal"
                           onClick="editModalWindow(${user.id})" data-target="#modalEdit">Edit</button>
                </td>
                <td>
                    <button class="btn btn-primary" id="table-cell-edit-button-2" data-toggle="modal"
                            onClick="deleteModalWindow(${user.id})" data-target="#modalDelete">Delete</button>
                </td>
            </tr>`
    }
    table_body.innerHTML = HTMLData
}

adminPage()