import * as axios from 'axios';
import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Container, InputGroup, FormControl, Button, Dropdown } from 'react-bootstrap'
import { backurl } from '../../properties';
import { authorizationHeader } from '../../actions';
import TaskBoard from './TaskBoard';

class SearchTasks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: this.props.match.params.name,
            tasks: [],
            searchEnabled: true,
            searchSuggestions: []
        }

        this.getTasks = this.getTasks.bind(this);
        this.getSuggestions = this.getSuggestions.bind(this);
        this.getSearchBoxText = this.getSearchBoxText.bind(this);
    }

    componentDidMount() {
        if (this.state.name)
            this.getTasks();
    }

    getTasks() {
        const link = '/tasks/search/' + this.state.name
        this.props.history.replace(link);

        axios.get(backurl + link, authorizationHeader())
            .then(response => {
                const tasks = response.data;
                this.setState({ tasks: tasks });
            })
            .catch((error) => {
                alert('error');
                this.setState({ status: error.response.status });
            });
    }

    getSuggestions() {
        const link = '/tasks/search/top/' + this.state.name
        this.props.history.replace(link);

        axios.get(backurl + link, authorizationHeader())
            .then(response => {
                const tasks = response.data;
                this.setState({ tasks: tasks });
            })
            .catch((error) => {
                alert('error');
                this.setState({ status: error.response.status });
            });
    }

    getSearchBoxText(value) {
        this.setState({name: value});
    }

    render() {
        return <Container>
            <h3> Search task by name</h3>
            <br />

            <InputGroup className="mb-3">
                <Dropdown>
                    <Dropdown.Toggle as={CustomToggle} getText={this.getSearchBoxText}>
                        {this.state.name}
                    </Dropdown.Toggle>
                    <Dropdown.Menu as={CustomMenu} onSelect={(v) => {console.log(JSON.stringify(v)); this.setState({name: v})}}>
                        {this.state.searchSuggestions.map(task => <Dropdown.Item>
                            {task.name}
                        </Dropdown.Item>)}

                        <Dropdown.Item>3</Dropdown.Item>
                        <Dropdown.Item>2</Dropdown.Item>
                        <Dropdown.Item>1</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>

                {/* <FormControl
                    placeholder="Search"
                    defaultValue={this.props.match.params.name}
                    onChange={(event) => this.setState({ name: event.target.value })}
                /> */}
                <InputGroup.Append>
                    <Button variant="outline-info" onClick={this.getTasks}>Search</Button>
                </InputGroup.Append>
            </InputGroup>



            <br />
            <h4> Search results for "{this.props.match.params.name}" </h4>
            <hr />
            {this.state.tasks.length > 0 ? <TaskBoard tasks={this.state.tasks} /> : 'Nothing found'}
        </Container>;
    }
}

class CustomToggle extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(e) {
        e.preventDefault();

        this.props.onClick(e);
    }

    render() {
        return <FormControl placeholder='Search...' defaultValue={this.props.children} onClick={this.handleClick} onChange={this.props.getText}/>
    }
}

class CustomMenu extends React.Component {
    constructor(props, context) {
      super(props, context);
  
      this.handleChange = this.handleChange.bind(this);
  
      this.state = { value: '' };
    }
  
    handleChange(e) {
      this.setState({ value: e.target.value.toLowerCase().trim() });
    }
  
    render() {
      const {
        children,
        style,
        className,
        'aria-labelledby': labeledBy,
      } = this.props;
  
      const { value } = this.state;
  
      return (
        <div style={style} className={className} aria-labelledby={labeledBy}>
          {/* <FormControl
            className="mx-3 my-2 w-auto"
            placeholder="Type to filter..."
            onChange={this.handleChange}
            value={value}
          /> */}
          <ul className="list-unstyled">
            {React.Children.toArray(children).filter(
              child =>
                !value || child.props.children.toLowerCase().startsWith(value),
            )}
          </ul>
        </div>
      );
    }
  }

export default withRouter(SearchTasks);