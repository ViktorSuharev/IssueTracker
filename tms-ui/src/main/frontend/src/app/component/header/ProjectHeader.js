import React from 'react';
import {Link} from 'react-router-dom';


class ProjectHeader extends React.Component {
    render() {
        return (
            <div className="d-flex flex-row">
                <div className="mt-1 py-2  flex-grow-1 ">
                    <h2>
                        Project: {this.props.project_name}
                    </h2>
                </div>


                <div className="d-flex mr-4 justify-content-end align-self-end mt-2" style={{"fontSize": "30px"}}>
                    <button type="button" className="btn btn-outline-success   btn-sm"><Link to="/info"> Info </Link>
                    </button>
                </div>

                {/*Отображение только при сессии админа или создателя */}
                <div className="d-flex mr-4 justify-content-end align-self-end " style={{"fontSize": "30px"}}>
                    {1 == 1 &&
                    <button type="button" className="btn btn-outline-success   btn-sm"><Link
                        to="/projectssettings"> Settings</Link></button>
                    }

                </div>


            </div>
        )
    }
}

export default ProjectHeader;
