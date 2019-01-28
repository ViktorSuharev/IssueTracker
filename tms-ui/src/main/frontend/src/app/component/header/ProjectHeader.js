import React from 'react';


export default function ProjectHeader({articles}) {
    const article = articles[0]
    return (
        <div className="d-flex flex-row">
            <div className="mt-1 py-2  flex-grow-1  badge badge-primary text-wrap ">
                Project: {article.title}
            </div>

            <div className="pl-sm-2 mr-4 bd-highlights align-self-end" style={{"fontSize":"30px"}}>
               by {article.creator}
            </div>

            <div className="d-flex mr-4 justify-content-end align-self-end mt-2" style={{"fontSize":"30px"}}>
                <button type="button" className="btn btn-outline-success   btn-sm"> Team</button>
            </div>

            <div className="d-flex mr-4 justify-content-end align-self-end " style={{"fontSize":"30px"}}>
              <button type="button" className="btn btn-outline-success   btn-sm">Settings</button>
            </div>



        </div>
    )
}

