<#import "template.ftl" as layout />
<@layout.mainLayout>
    <a href="/user?action=new" class="btn btn-secondary float-right mb-1" role="button">New User</a>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Family</th>
            <th scope="col">Patronymic</th>
            <th scope="col">Email</th>
            <th scope="col">Phone</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as person>
            <tr>
                <td>${person.id}</td>
                <td>${person.name}</td>
                <td>${person.family}</td>
                <td>${person.patronymic}</td>
                <td>${person.email}</td>
                <td>${person.phone}</td>
                <td>
                    <a href="/user?action=edit&id=${person.id}" class="btn btn-secondary float-right mr-2" role="button">Edit</a>
                    <a href="/delete?id=${person.id}" class="btn btn-danger float-right mr-2" role="button">Delete</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@layout.mainLayout>