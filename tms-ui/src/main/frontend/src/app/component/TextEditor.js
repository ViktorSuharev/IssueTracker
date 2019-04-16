import React, { Component, PropTypes } from 'react';
import RichTextEditor from 'react-rte';
import { Button } from 'react-bootstrap';

export default class TextEditor extends Component {
    constructor(props) {
        super(props);
        //   static propTypes = {
        //     onChange: PropTypes.func
        //   };
        this.state = {
            value: RichTextEditor.createEmptyValue(),
            placeholder: props.placeholder,
            onSave: props.onSave,
            isSaving: true
        }

        this.onSave = this.onSave.bind(this);
    }

    onChange = (value) => {
        this.setState({ value });
        if (this.props.onChange) {
            //   Send the changes up to the parent component as an HTML string.
            // This is here to demonstrate using `.toString()` but in a real app it
            // would be better to avoid generating a string on each change.
            this.props.onChange(
                value.toString('markdown')
            );
        }
    };

    onSave(event) {
        event.preventDefault();
        this.state.onSave(this.state.value.toString('markdown'));
    }

    render() {
        const { isSaving } = this.state.isSaving;
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