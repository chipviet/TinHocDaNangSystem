import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/production">
      <Translate contentKey="global.menu.entities.production" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/category">
      <Translate contentKey="global.menu.entities.category" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/brand">
      <Translate contentKey="global.menu.entities.brand" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/guarantee">
      <Translate contentKey="global.menu.entities.guarantee" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/guarantee-production">
      <Translate contentKey="global.menu.entities.guaranteeProduction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/promotion">
      <Translate contentKey="global.menu.entities.promotion" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/promotion-production">
      <Translate contentKey="global.menu.entities.promotionProduction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cart">
      <Translate contentKey="global.menu.entities.cart" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cart-production">
      <Translate contentKey="global.menu.entities.cartProduction" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
