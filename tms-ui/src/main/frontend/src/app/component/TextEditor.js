import React, { Component } from 'react';
import RichTextEditor from 'react-rte';
import { Button } from 'react-bootstrap';

export default class TextEditor extends Component {
    constructor(props) {
        super(props);

        let onSave = null;
        let readOnly = false;
        let placeholder = '';
        let maxLength = 2000;
        if(props){
            if(props.onSave)
                onSave = props.onSave;
            if(props.readOnly)
                readOnly = props.readOnly;
            if(props.placeholder)
                placeholder = props.placeholder;
            if(props.maxLength)
                maxLength = props.maxLength;
        }

        let v = props && props.value
                ? RichTextEditor.createValueFromString(props.value, 'markdown')
                : RichTextEditor.createEmptyValue();

        this.state = {
            value: v,
            placeholder: placeholder,
            onSave: onSave,
            readOnly: readOnly,
            maxLength: maxLength
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

    _handleBeforeInput = () => {
        const currentContent = this.state.editorState.getCurrentContent();
      const currentContentLength = currentContent.getPlainText('').length
  
        if (currentContentLength > this.state.maxLength - 1) {
          console.log('you can type max ten characters');
  
          return 'handled';
      }
    }
    
    _handlePastedText = (pastedText) => {
        const currentContent = this.state.editorState.getCurrentContent();
        const currentContentLength = currentContent.getPlainText('').length
  
        if (currentContentLength + pastedText.length > this.state.maxLength) {
          console.log('you can type max ten characters');
  
          return 'handled';
      }
    }
    
    _handleChange = (editorState) => {
      this.setState({ editorState });
    }

    render() {
        return (
            <RichTextEditor
                value={this.state.value}
                readOnly={this.state.readOnly}
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