import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import GenService from '../service/GenService';
import UserService from '../service/UserService';
import Message from './Message';

class User extends Component {

    constructor(props) {
        super(props)
        this.state = {
            userList: [],
            msgObj: {
              msg: '',
              isError: true
            }
        }
    }

    componentDidMount() {
        this.getUserList();
    }

    getUserList(){
        UserService.getUserList()
        .then(res => {
            this.updateMessage('');
            this.setState({ userList: res.data })
        })
        .catch(err => {
            this.updateMessage(err, true);
        });
    }

    deleteUser(userId){
        UserService.deleteUser(userId)
        .then(res => {
            this.getUserList();
        })
        .catch(err => {
            this.updateMessage(err, true);
        });
    }

    updateMessage(msg, isError = false){
        this.setState({msgObj: {msg:msg, isError:isError}});
    }

    formatDateTime(dtTime){
        return GenService.formatDateTime(dtTime);
    }

    render(){
        return (
            <div>
                <Message msgObj={this.state.msgObj} />
                <div className="button-region">
                    <Link to='/UserCreate' className="button">Create</Link>
                </div>
                <div className="report-region-2">
                    <table cellPadding="0" border="0" cellSpacing="0" summary="" className="report-standard">
                        <thead>
                            <tr >
                                <th></th>
                                <th></th>
                                <th>User ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Active</th>
                                <th>Date Created</th>
                                <th>User Created</th>
                                <th>Date Modified</th>
                                <th>User Modified</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.userList.map((user) => (
                                <tr key={user.userId} className="highlight-row">
                                    <td><Link to={`/UserEdit/${user.userId}`}>Edit</Link></td>
                                    <td><a href="#" onClick = {this.deleteUser.bind(this, user.userId)}>Delete</a></td>
                                    <td>{user.userId}</td>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    {user.active && <td align="center"><input type="checkbox" checked disabled /></td>}
                                    {!user.active && <td align="center"><input type="checkbox" disabled /></td>}
                                    <td>{this.formatDateTime(user.dateCrt)}</td>
                                    <td>{user.userCrtObj != null && user.userCrtObj.firstName} {user.userCrtObj != null && user.userCrtObj.lastName}</td>
                                    <td>{this.formatDateTime(user.dateMod)}</td>
                                    <td>{user.userModObj != null && user.userModObj.firstName} {user.userModObj != null && user.userModObj.lastName}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default User;