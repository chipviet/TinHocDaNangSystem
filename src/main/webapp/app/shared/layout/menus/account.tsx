import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import './menus.scss';

const accountMenuItemsAuthenticated = (
  <>
    <MenuItem icon="wrench" to="/account/settings">
      <span className="brand-item">
        <Translate contentKey="global.menu.account.settings">Settings</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="lock" to="/account/password">
      <span className="brand-item">
        <Translate contentKey="global.menu.account.password">Password</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout">
      <span className="brand-item">
        <Translate contentKey="global.menu.account.logout">Sign out</Translate>
      </span>
    </MenuItem>
  </>
);

const accountMenuItems = (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login">
      <span className="brand-item">
        <Translate contentKey="global.menu.account.login">Sign in</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="sign-in-alt" to="/account/register">
      <span className="brand-item">
        <Translate contentKey="global.menu.account.register">Register</Translate>
      </span>
    </MenuItem>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <NavDropdown className="brand-item" icon="user" name={translate('global.menu.account.main')} id="account-menu">
    {isAuthenticated ? accountMenuItemsAuthenticated : accountMenuItems}
  </NavDropdown>
);

export default AccountMenu;
