import React from 'react';

export default function TaskListHeader() {


    return (
        <div>

            <div>
                <div className="  mx-2 "   >
                    <button className="btn btn-outline-warning  btn-sm">
                        Создать таск
                    </button>
                </div>

            </div>

                <div  className=" d-flex flex-row justify-content-center align-items-center">
                    Tasks list
                </div>




            <div className="table-responsive">
                <table className="table table-light table-striped table-bordered table-hover table-sm  ">
                    <thead className="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Название</th>
                        <th scope="col">Приоритет</th>
                        <th scope="col">assigned</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>@mdo</td>
                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>@fat</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>Larry</td>
                        <td>the Bird</td>
                        <td>@twitter</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>Larry</td>
                        <td>the Bird</td>
                        <td>@twitter</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>Larry</td>
                        <td>the Bird</td>
                        <td>@twitter</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>Larry</td>
                        <td>the Bird</td>
                        <td>@twitter</td>
                    </tr>
                    </tbody>
                </table>
            </div>


        </div>
    )

}