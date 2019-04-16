import React, { Component } from 'react';
import RichTextEditor from 'react-rte';
import { Button } from 'react-bootstrap';

export default class TextEditor extends Component {
    constructor(props) {
        super(props);

        let onSave = null;
        let readOnly = false;
        let placeholder = '';
        if(props){
            if(props.onSave)
                onSave = props.onSave;
            if(props.readOnly)
                readOnly = props.readOnly;
            if(props.placeholder)
                placeholder = props.placeholder;
        }

        this.state = {
            value: RichTextEditor.createEmptyValue(),
            placeholder: placeholder,
            onSave: onSave,
            isSaving: true,
            readOnly: readOnly
        }

        this.onSave = this.onSave.bind(this);
    }

    onChange = (value) => {
        this.setState({ value });
    };

    onSave(event) {
        event.preventDefault();
        this.state.onSave(this.state.value.toString('markdown'));
    }

    render() {
        return (
            <RichTextEditor
                value={this.state.value}
                onChange={this.onChange}
                placeholder={this.state.placeholder}
                customControls={[
                    () => { return <Button  
                                        variant='dark' 
                                        onClick={this.onSave}
                                        size='sm'>
                                Save
                            </Button>
                    }
                ]}
            />
        );
    }
}