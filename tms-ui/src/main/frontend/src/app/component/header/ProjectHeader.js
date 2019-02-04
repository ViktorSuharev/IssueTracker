import React from 'react';
import {Link} from 'react-router-dom';


class ProjectHeader extends React.Component{
    render() {
        return (
            <div className="d-flex flex-row">
                <div className="mt-1 py-2  flex-grow-1  badge badge-primary text-wrap ">
                    Project: {this.props.project_name}
                </div>

                <div className="pl-sm-2 mr-4 bd-highlights align-self-end" style={{"fontSize": "30px"}}>
                    by {this.props.creator_id}
                </div>

                <div className="d-flex mr-4 justify-content-end align-self-end mt-2" style={{"fontSize": "30px"}}>
                    <button type="button" className="btn btn-outline-success   btn-sm"><Link to="/team"> Team </Link></button>
                </div>

                <div className="d-flex mr-4 justify-content-end align-self-end " style={{"fontSize": "30px"}}>
                    <button type="button" className="btn btn-outline-success   btn-sm"><Link to="/projectssettings"> Settings</Link></button>
                </div>


            </div>
        )
    }
}
export  default ProjectHeader;
