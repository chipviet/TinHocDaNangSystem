import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cart from './cart';
import CartDetail from './cart-detail';
import CartUpdate from './cart-update';
import CartDeleteDialog from './cart-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CartUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CartUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CartDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cart} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CartDeleteDialog} />
  </>
);

export default Routes;
