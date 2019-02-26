import React from 'react';
import {Link} from 'react-router-dom';


class ProjectHeader extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: this.props.projectId,
        };
    }

    render() {
        return (
            <div className="d-flex flex-row">
                <div className="mt-1 py-2  flex-grow-1 ">
                    <h2>
                        Project: {this.props.project_name}
                    </h2>
                </div>


                <div className="d-flex mr-4 justify-content-end align-self-end mt-2" style={{"fontSize": "30px"}}>
                    <button type="button" className="btn btn-outline-success   btn-sm"><Link to={`/projectinfo/${this.state.projectId}`}> Info </Link>
                    </button>
                </div>

                {/*Display only during the session of the admin or creator*/}
                <div className="d-flex mr-4 justify-content-end align-self-end " style={{"fontSize": "30px"}}>
                    {1 == 1 &&
                    <button type="button" className="btn btn-outline-success   btn-sm"><Link
                        to={`/projectssettings/${this.state.projectId}`}> Settings</Link></button>
                    }

                </div>


            </div>
        )
    }
}

export default ProjectHeader;
