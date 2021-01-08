import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CartProduction from './cart-production';
import CartProductionDetail from './cart-production-detail';
import CartProductionUpdate from './cart-production-update';
import CartProductionDeleteDialog from './cart-production-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CartProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CartProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CartProductionDetail} />
      <ErrorBoundaryRoute path={match.url} component={CartProduction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CartProductionDeleteDialog} />
  </>
);

export default Routes;
